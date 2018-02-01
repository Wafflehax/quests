const fs = require('fs');
const path = require('path');
const loader = require('./Loader');
const Formatter = require('./Formatter.js');

class ReportGenerator {

    constructor(target_dir, formatting) {
        this.root_dir = path.resolve('.');
        this.formatting = formatting;
        this.target_name = path.basename(target_dir);
        this.target_dir = target_dir;
        this.target_settings = loader.load_target_settings(this.target_dir);
        this.report_settings = loader.load_report_settings(this.target_dir);
        this.template_reg_exp = new RegExp(this.report_settings.template_reg_exp, 'g');
        this.template = loader.load_template(this.target_dir);
    }

    table(target) {

        const latex_string = [];
        latex_string.push(this.formatting.section_title(target));

        latex_string.push(this.formatting.table(target.file_content));
        return latex_string.join('\n');
    }

    individual_contribution(target) {

        //Output string

        const latex_string = [];
        latex_string.push(this.formatting.section_title(target));

        //Subsections in contributions file

        const subsections = target.file_content;
        const subsection_keys = Object.keys(subsections);

        for (let i = 0; i < subsection_keys.length; i++) {

            let current_subsection = subsections[subsection_keys[i]];

            //Make title for subsection

            latex_string.push(this.formatting.subsection_title(subsection_keys[i]));

            //Pick what to do depending on type of input

            let action;
            switch (current_subsection.constructor) {

                case Array:
                    action = this.formatting.itemize;
                    break;

                default:
                   action = this.formatting.single_value;
            }
            latex_string.push(action(current_subsection));
        }

        return latex_string.join('\n');
    }

    /***
     * Loads, processes target files, and compiles all the generated latex strings into an object
     * @returns {{Template Data}}
     */

    generate_template_data(){

        const targets = this.target_settings;
        const template_data = {};
        for(let i = 0; i < targets.length; i++){

            const current_target = targets[i];

            loader.load_target(this.target_dir, current_target);
            template_data[current_target.id] = this.process_target(current_target);
        }

        return template_data;
    }

    /***
     * Merges template data from targets with the empty skeleton and outputs a latex string
     * @param template_data
     */

    generate_latex_string(template_data){

        template_data = template_data || this.generate_template_data();
        return this.fill_template(this.template, this.generate_template_data());
    }

    process_target(target){

        switch(target.options.display){

            case "individual_contribution":
                console.log(`Making contribution section: ${target.id}`);
                return this.individual_contribution(target);

            case "table":
                console.log(`Making table section: ${target.id}`);
                return this.table(target);

            default:
                console.log('error');
        }
    }

    /***
     * Replaces placeholders in a string with the attributes in data
     * @param string String with placeholders to replace
     * @param data Data to be injected into string
     * @returns {string}
     */

    fill_template(string, data) {

        string = string.replace(this.template_reg_exp, (match, capture) => {
            return data[capture];
        });

        return string;
    }


    /***
     * Calls generate_latex_string() and saves the output as a file to: target_dir/target.tex
     * @param latex_string
     */

    write_to_file(latex_string){

        latex_string = latex_string || this.generate_latex_string();

        fs.writeFileSync(path.resolve(this.target_dir, `${this.target_name}.tex`), latex_string);
    }
}

module.exports = ReportGenerator;