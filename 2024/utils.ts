import * as fs from 'node:fs';

function parseFile(file: string) {
    return fs.readFileSync(file).toString('utf-8');
}

export {
    parseFile
};