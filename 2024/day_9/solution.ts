import { parseFile } from '../utils.ts';

function reorganiseDrive(input: string, sortingFunction: (input: string[]) => string[]): number {
    const hardDriveRepresentation = convertBlocksToHardDriveRepresentation(input);
    const reorganisedHardDrive = sortingFunction(hardDriveRepresentation);
    return calculateChecksum(reorganisedHardDrive);
}

function convertBlocksToHardDriveRepresentation(input: string): string[] {
    const representation = new Array<string>();
    input.split("").forEach((char, index) => {
        const idNumber = (index / 2).toString();
        for (let i = 0; i < Number(char); i++) {
            representation.push(index % 2 === 0 ? idNumber : ".")
        }
    });
    return representation;
}

function reorganiseHardDriveByBlock(input: string[]): string[] {
    const organisedDrive = [...input];
    while (organisedDrive.indexOf(".") !== -1) {
        const lastChar = organisedDrive.pop();
        if (lastChar !== undefined && lastChar !== ".") {
            organisedDrive.splice(organisedDrive.indexOf("."), 1, lastChar);
        }
    }

    return organisedDrive;
}

function reorganiseHardDriveByFile(input: string[]): string[] {
    const organisedDrive = [...input];
    let currentKey = Number(organisedDrive[organisedDrive.length - 1]);
    while (currentKey >= 0) {
        const keyStart = organisedDrive.indexOf(currentKey.toString());
        const keyEnd = organisedDrive.lastIndexOf(currentKey.toString());
        const fileLength = keyEnd - keyStart + 1;
        
        const firstAvailableSpace = organisedDrive.slice(0, keyStart).findIndex((_, index) => organisedDrive.slice(index, index + fileLength).every(item => item === "."));
        if (firstAvailableSpace !== -1 && firstAvailableSpace < keyStart) {
            organisedDrive.splice(firstAvailableSpace, fileLength, ...currentKey.toString().repeat(fileLength).split(""))
            organisedDrive.splice(keyStart, fileLength, ...".".toString().repeat(fileLength).split(""));
        }
        currentKey--;
    }

    return organisedDrive;
}

function calculateChecksum(input: string[]): number {
    return input.map((value, index) => {
        return value !== "." ? Number(value) * index : 0;
    }).reduce((prev, curr) => prev + curr);
}

console.log(reorganiseDrive(parseFile("day_9/input.txt"), (input) => reorganiseHardDriveByBlock(input))); // 6283170117911
console.log(reorganiseDrive(parseFile("day_9/input.txt"), (input) => reorganiseHardDriveByFile(input))); // 21196240522055 is too high

export {
    reorganiseDrive
}