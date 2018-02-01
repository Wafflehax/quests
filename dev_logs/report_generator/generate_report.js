const child_process = require('child_process');
const path = require('path');
const Generator = require('./ReportGenerator');
const Formatter = require('./Formatter');
const arg = {};

//Parse args

arg.node_bin = process.argv[0];
arg.working_dir = process.argv[1];
arg.target = path.resolve(process.argv[2]);
arg.target_basename = `${arg.target}/${path.basename(arg.target)}`;

//Instantiate Generator

gen = new Generator(arg.target, Formatter);
process.chdir(arg.target);
child_process.exec(`pdflatex -synctex=1 -interaction=nonstopmode ${arg.target_basename}.tex`);
gen.write_to_file();
