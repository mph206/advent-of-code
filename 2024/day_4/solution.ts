import { Coordinate } from "../utils/interfaces.ts";
import { parseFile } from '../utils/file_parsing.ts';

type stringSearchFunction = (searchString: string, coordinate: Coordinate, parsedInput: string[][]) => number;

function findStringInGrid(input: string, callback: stringSearchFunction): number {
    const parsedInput = parseFile(input).split("\n").map(line => line.split(""));
    return parsedInput
        .map((line, lineIndex) => {
            return line
                .map((_, charIndex) => callback("Defined outside this function", { x: charIndex, y: lineIndex }, parsedInput))
                .reduce((acc, current) => acc + current);
        })
        .reduce((previous, current) => previous + current);
}

function findStraightString(searchString: string, coordinate: Coordinate, grid: string[][]): number {
    return [
        { x: 1, y: 0 },
        { x: 1, y: 1 },
        { x: 0, y: 1 },
        { x: -1, y: 1 },
        { x: -1, y: 0 },
        { x: -1, y: -1 },
        { x: 0, y: -1 },
        { x: 1, y: -1 }
        ]
        .map(direction => {
            const string = 
                grid[coordinate.y][coordinate.x]
                    .concat(...[1, 2, 3].map(distance => {
                        const line = grid[coordinate.y + direction.y * distance];
                        return line === undefined || line[coordinate.x + direction.x * distance] === undefined 
                            ? "Not found"
                            : line[coordinate.x + direction.x * distance];
                }));
            return string === searchString;
        })
        .filter(found => found)
        .length
}

function findXString(searchString: string, coordinate: Coordinate, grid: string[][]): number {
    if (!xShapeIsInBounds(coordinate, grid)) {
        return 0;
    }

    const lineOne = [{ x: 1, y: 1 }, { x: 0, y: 0 }, { x: -1, y: -1 }].map(coord => ({ x: coordinate.x + coord.x, y: coordinate.y + coord.y }));
    const lineTwo = [{ x: -1, y: 1 }, { x: 0, y: 0 }, { x: 1, y: -1 }].map(coord => ({ x: coordinate.x + coord.x, y: coordinate.y + coord.y }));
    const wordOne = lineOne.map(coord => grid[coord.y][coord.x]).join("");
    const wordTwo = lineTwo.map(coord => grid[coord.y][coord.x]).join("");

    return [wordOne, wordOne.split("").reverse().join(""), wordTwo, wordTwo.split("").reverse().join("")]
        .filter(string => string === searchString)
        .length === 2 
            ? 1
            : 0
}

function xShapeIsInBounds(coordinate: Coordinate, grid: string[][]) {
    return coordinate.x > 0 && coordinate.y > 0 && coordinate.x < grid[0].length - 1 && coordinate.y < grid.length - 1
}

console.log(findStringInGrid("day_4/input.txt", (_, coordinate, parsedInput) => findStraightString("XMAS", coordinate, parsedInput))) // 2593
console.log(findStringInGrid("day_4/input.txt", (_, coordinate, parsedInput) => findXString("MAS", coordinate, parsedInput))) // 1950
