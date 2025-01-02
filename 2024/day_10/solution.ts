import { buildAdjacencyList, dfs } from "../utils/dfs.ts";
import { Coordinate } from "../utils/interfaces.ts";
import { stringToGrid, parseFile } from '../utils/file_parsing.ts';

type ScoringFunction = (adjacencyList: Map<string, Coordinate[]>, start: Coordinate, grid: string[][]) => number;

function scoreTrailheads(input: string, scoringFunction: ScoringFunction): number {
    const grid = stringToGrid(input);
    const nineSquareLocations = findStartCoordinates(grid);
    const adjacencyList = buildAdjacencyList(grid, isValidPath);
    return findTrailheadScores(nineSquareLocations, adjacencyList, grid, scoringFunction);
}

function findStartCoordinates(input: string[][]): Coordinate[] {
    return input.flatMap((line, lineIndex) => {
        return line.map((char, charIndex) => {
            return char === '0' ? new Coordinate(charIndex, lineIndex) : undefined;
        } )
    })
    .filter(item => item !== undefined)
}

const isValidPath = (char: string, coord: Coordinate, grid: string[][]) => Number(char) - Number(grid[coord.y][coord.x]) === -1;

function findTrailheadScores(
    nineSquareLocations: Coordinate[], 
    adjacencyList: Map<string, Coordinate[]>, 
    grid: string[][], 
    scoringFunction: ScoringFunction
): number {
    return nineSquareLocations
        .map(start => scoringFunction(adjacencyList, start, grid))
        .reduce((a, b) => a + b);
}

function countNinesVisited(adjacencyList: Map<string, Coordinate[]>, start: Coordinate, grid: string[][]): number {
    return new Set(
        dfs(adjacencyList, start, false)
            .filter(coord => grid[coord.y][coord.x] !== undefined && grid[coord.y][coord.x] === '9')
            .map(coord => coord.toString())
    )
        .size;
}

function countPossiblePaths(adjacencyList: Map<string, Coordinate[]>, start: Coordinate, grid: string[][]): number {
    return dfs(adjacencyList, start, true)
        .filter(coord => grid[coord.y][coord.x] !== undefined && grid[coord.y][coord.x] === '9')
        .length
}

console.log(scoreTrailheads(parseFile("day_10/input.txt"), (adjacencyList, start, grid) => countNinesVisited(adjacencyList, start, grid))); // 472
console.log(scoreTrailheads(parseFile("day_10/input.txt"), (adjacencyList, start, grid) => countPossiblePaths(adjacencyList, start, grid))); // 969

export {
    scoreTrailheads
}
