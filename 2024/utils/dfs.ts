import { Coordinate } from "./interfaces.ts";

type AdjacencyList = Map<string, Coordinate[]>
type ValidityFilterFunction = (char: string, coord: Coordinate, grid: string[][]) => boolean;

function buildAdjacencyList(grid: string[][], validityFilterFunction: ValidityFilterFunction): AdjacencyList {
    const map: AdjacencyList = new Map();
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
                    .filter(coord => validityFilterFunction(char, coord, grid))
            );
        });
    });
    return map;
}

const isInBounds = (coord: Coordinate, grid: string[][]) => coord.x >= 0 && coord.y >= 0 && coord.x < grid[0].length && coord.y < grid.length;

function dfs(adjacencyList: AdjacencyList, start: Coordinate, revisitVisitedCoordinates: boolean): Coordinate[] {
    const stack = [start];
    const visited = new Set();
    const result = [];
    // Sometimes we want to know the full path visited, not just whether the destination was reached
    const shouldRevisitVisited = (vertex: Coordinate) => revisitVisitedCoordinates ? true : !(visited.has(vertex));

    while (stack.length) {
        const vertex = stack.pop();

        if (vertex !== undefined && shouldRevisitVisited(vertex)) {
            visited.add(vertex);
            result.push(vertex);

            for (const neighbor of adjacencyList.get(vertex.toString())!) {
                stack.push(neighbor);
            }
        }
    }

    return result;
}

export {
    dfs, 
    buildAdjacencyList,
}

export type {
    AdjacencyList
}