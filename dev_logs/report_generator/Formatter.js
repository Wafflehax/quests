class Formatter {

    static section_title(target){

        return `\\${target.options.section_type}*{${target.id}}\n`;
    }

    static subsection_title(title){
        return `\\subsubsection*{${title}}`;
    }

    static single_value(value){
        return value;
    }

    static table(items){

        const table_headers = Object.keys(items[0]);
        const latex_string = [];


        let columns = new Array(table_headers.length + 1).join('l ');
        latex_string.push(`\\begin{center}`);
        latex_string.push(`\\begin{tabular}{${columns}}`);
        latex_string.push(table_headers.join(' & ') + `\\\\`);
        latex_string.push(`\\hline\\\\`);

        for(let i = 0; i < items.length; i++){

            latex_string.push(Object.values(items[i])
                .map((str)=>{
                    if(!str)
                        return '--';

                    return str;
                }).join(' & ') + '\\\\');
        }
        latex_string.push(`\\end{tabular}`);
        latex_string.push(`\\end{center}`);

        return latex_string.join('\n');
    }

    static itemize(items){
        const latex_string = [];
        latex_string.push(`\\begin{itemize}`);
        for(let i = 0; i < items.length; i++){

            latex_string.push(`\\item ${items[i]}`);
        }
        latex_string.push(`\\end{itemize}`);
        return latex_string.join('\n');
    }

    static other(){
        return `This is other`;
    }
}

module.exports = Formatter;