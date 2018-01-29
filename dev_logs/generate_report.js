const fs = require('fs');
const path = require('path');


//Target dir

const target = process.argv[2];
const empty_report = fs.readFileSync(path.resolve('.', 'empty_report.tex'), 'utf8');

//Constants

const pattern = /%(.+)%/g;
const lb = /\r\n|\n\r|\n|\r/g;//Line break

function write_contributions(input_file){

    input_file = fs.readFileSync(input_file, 'utf8');
    input_file = JSON.parse(input_file);
    let latex = [];

    const keys = Object.keys(input_file);
    for(let i = 0; i < keys.length; i++){

        latex.push(`\\subsubsection*{${keys[i]}}\n\\begin{itemize}`);
        const section = input_file[keys[i]];
        for(let j = 0; j < section.length; j++){
            latex.push(`\\item ${section[j]}\n`);
        }
        latex.push(`\\end{itemize}`)
    }

    return latex.join('\n');
}

function replace_placeholders(template_data){

    while(empty_report.replace(pattern, (match) => template_data[match[1]] || ''));
}

//Template data


const template_data = {
    "frerik": write_contributions(path.resolve(target, "frerik.json")),
    "joe": write_contributions(path.resolve(target, "joe.json")),
    "kevin": write_contributions(path.resolve(target, "kevin.json")),
    "patrick":write_contributions(path.resolve(target, "patrick.json")),
    "team": "",
};



fs.writeFileSync(path.resolve(target, "report.tex"), latex_template(template_data));
