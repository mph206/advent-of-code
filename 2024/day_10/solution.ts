import { Coordinate } from "../interfaces.ts";
import { stringToGrid, parseFile } from '../utils.ts';

type ScoringFunction = (adjacencyList: Map<string, Coordinate[]>, start: Coordinate, grid: string[][]) => number;

function scoreTrailheads(input: string, scoringFunction: ScoringFunction): number {
    const grid = stringToGrid(input);
    const nineSquareLocations = findStartCoordinates(grid);
    const adjacencyList = buildAdjacencyList(grid);
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

function buildAdjacencyList(grid: string[][]): Map<string, Coordinate[]> {
    const map = new Map<string, Coordinate[]>();
    grid.forEach((line, lineIndex) => {
        line.forEach((char, charIndex) => {
            map.set(
                new Coordinate(charIndex, lineIndex).toString(), 
                [
                    new Coordinate(charIndex, lineIndex - 1), 
                    new Coordinate(charIndex, lineIndex + 1), 
                    new Coordinate(charIndex + 1, lineIndex), 
                    new Coordinate(charIndex - 1, lineIndex), 
                ]
                    .filter(coord => isInBounds(coord, grid))
                    .filter(coord => isValidPath(char, coord, grid))
            );
        });
    });
    return map;
}

const isInBounds = (coord: Coordinate, grid: string[][]) => coord.x >= 0 && coord.y >= 0 && coord.x < grid[0].length && coord.y < grid.length;

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
        dfs(adjacencyList, start)
            .filter(coord => grid[coord.y][coord.x] !== undefined && grid[coord.y][coord.x] === '9')
            .map(coord => coord.toString())
    )
        .size;
}

function countPossiblePaths(adjacencyList: Map<string, Coordinate[]>, start: Coordinate, grid: string[][]): number {
    return dfs(adjacencyList, start)
        .filter(coord => grid[coord.y][coord.x] !== undefined && grid[coord.y][coord.x] === '9')
        .length
}

function dfs(adjacencyList: Map<string, Coordinate[]>, start: Coordinate): Coordinate[] {
    const stack = [start];
    const visited = new Set();
    const result = [];

    // Usually DFS doesn't re-visit visited vertexes, but we do here because we want to find all paths in part 2
    while (stack.length) {
        const vertex = stack.pop();

        if (vertex !== undefined) {
            visited.add(vertex);
            result.push(vertex);

            for (const neighbor of adjacencyList.get(vertex.toString())!) {
                stack.push(neighbor);
            }
        }
    }

    return result;
}

console.log(scoreTrailheads(parseFile("day_10/input.txt"), (adjacencyList, start, grid) => countNinesVisited(adjacencyList, start, grid))); // 472
console.log(scoreTrailheads(parseFile("day_10/input.txt"), (adjacencyList, start, grid) => countPossiblePaths(adjacencyList, start, grid))); // 969

export {
    scoreTrailheads
}
