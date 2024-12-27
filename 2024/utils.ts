import * as fs from 'node:fs';

function parseFile(file: string) {
    return fs.readFileSync(file).toString('utf-8');
}

function parseToLines(file: string): string[] {
    return parseFile(file).split("\n");
}

export {
    parseFile,
    parseToLines
};