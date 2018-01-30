const fs = require('fs');
const path = require('path');

//Modules

const template_engine = require('./report_generator/template_engine');


//Target dir

const target = process.argv[2];
if(!target)
    return;

const empty_report = path.resolve('report_generator', 'empty_report.tex');

//Constants

const pattern = /%(.+)%/g;
const lb = /\r\n|\n\r|\n|\r/g;//Line break

function write_individual_contributions(input_file){

    input_file = fs.readFileSync(input_file, 'utf8');
    input_file = JSON.parse(input_file);
    let latex = [];

    const keys = Object.keys(input_file);
    for(let i = 0; i < keys.length; i++){

		const section = input_file[keys[i]];

		latex.push(`\\subsubsection*{${keys[i]}}`);
        if(section.constructor === Array){
			latex.push(`\\begin{itemize}`);
			for(let j = 0; j < section.length; j++){
				latex.push(`\\item ${section[j]}\n`);
			}
			latex.push(`\\end{itemize}`)
        }

        else {
            latex.push(`${section}`)
        }
    }

    return latex.join('\n');
}

function write_team_features(input_file) {

	input_file = fs.readFileSync(input_file, 'utf8');
	input_file = JSON.parse(input_file);

	//Features

	const features_latex = [];
	const features = input_file.features;
	for (let i = 0; i < features.length; i++) {

		const current_feature = features[i];

		let current_feature_latex = `${current_feature.id} & ${current_feature.description} & ${current_feature.commit_date}\\\n`;
		features_latex.push(current_feature_latex);
	}

	return features_latex.join('\n');
}

function write_team_tests(input_file) {

	input_file = fs.readFileSync(input_file, 'utf8');
	input_file = JSON.parse(input_file);

    //Tests

	const tests_latex = [];
	const tests = input_file.tests;
	for(let i = 0; i < tests.length; i++){

		const current_tests = tests[i];

		let current_tests_latex = `${current_tests.id} & ${current_tests.description} & ${current_tests.commit_date}\\\n`;
		tests_latex.push(current_tests_latex);
	}

	return tests_latex.join('\n');
}

//Template data


const template_data = {
    "frerik": write_individual_contributions(path.resolve(target, "frerik.json")),
    "joe": write_individual_contributions(path.resolve(target, "joe.json")),
    "kevin": write_individual_contributions(path.resolve(target, "kevin.json")),
    "patrick":write_individual_contributions(path.resolve(target, "patrick.json")),
    "team_features": write_team_features(path.resolve(target, "team.json")),
    "team_tests": write_team_tests(path.resolve(target,"team.json"))
};



//fs.writeFileSync(path.resolve(target, "report.tex"), latex_template(template_data));
template_engine(empty_report, template_data, (err, out)=>{

    if(err)
        return console.log(err);

    fs.writeFileSync(path.resolve(target, `report-${target}.tex`), out);
});