import * as fs from 'node:fs';

function parseFile(file: string) {
    return fs.readFileSync(file).toString('utf-8');
}

function parseToLines(file: string): string[] {
    return parseFile(file).split("\n");
}

function stringToGrid(string: string): string[][] {
    return string.split("\n").map(line => line.trim().split(""));
}

export {
    parseFile,
    parseToLines,
    stringToGrid
};