const fs = require('fs');
const target = 'featureTable.json';

let featureTable = JSON.parse(fs.readFileSync(target).toString());
let output = [];

let keys = Object.keys(featureTable);
for(let i = 0; i < keys.length; i++){

    let feature = featureTable[keys[i]];

    if(feature){

        output.push({
            "id": keys[i],
            "description": feature[0],
            "status": feature[1],
            "date": feature[2]
        });
    }
}

output = JSON.stringify(output, '' ,2);
console.log(output);
fs.writeFileSync(target, output);