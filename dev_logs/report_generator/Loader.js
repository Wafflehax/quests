const fs = require('fs');
const path = require('path');
const root_dir = path.resolve('.');

class Loader{
    static load_target_settings(target_dir){
        let target_settings_file;
        try {
            target_settings_file = fs.readFileSync(path.resolve(target_dir, 'target_settings.json'));
        }

        catch(err){
            target_settings_file = fs.readFileSync(path.resolve(root_dir, 'target_settings.json'));
        }

        return JSON.parse(target_settings_file);
    }

    static load_report_settings(target_dir){
        let section_settings_file;
        try{
            section_settings_file = fs.readFileSync(path.resolve(target_dir, 'report_settings.json'));
        }

        catch(err){
            section_settings_file = fs.readFileSync(path.resolve(root_dir, 'report_settings.json'));
        }

        return JSON.parse(section_settings_file);
    }

    static load_template(target_dir){

        let template;

        try{
            template = fs.readFileSync(path.resolve(target_dir, 'template.tex'));
        }

        catch(err){
            template = fs.readFileSync(path.resolve(root_dir, 'template.tex'));
        }

        return template.toString();
    }

    static load_target(target_dir, target){

        let filename = path.resolve(target_dir, target.file);
        let file_content;

        try{
            file_content = fs.readFileSync(filename);
            file_content = JSON.parse(file_content.toString());
        }

        catch(error){
            console.error(error);
            file_content = {};
        }

        target['file'] = filename;
        target['file_content'] = file_content;
        return target;
    }
}

module.exports = Loader;