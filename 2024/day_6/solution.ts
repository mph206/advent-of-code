import { Coordinate, Direction } from "../interfaces.ts";
import { parseFile } from '../utils.ts';

const directionMap: Map<Direction, { coord: Coordinate; direction: Direction }> = new Map([
    [Direction.UP, {coord: new Coordinate(0, -1), direction: Direction.RIGHT}],
    [Direction.RIGHT, {coord: new Coordinate(1, 0), direction: Direction.DOWN}],
    [Direction.DOWN, {coord: new Coordinate(0, 1), direction: Direction.LEFT}],
    [Direction.LEFT, {coord: new Coordinate(-1, 0), direction: Direction.UP}]
]);

function partOne(input: string): number {
   const grid = parseInput(input);
   const startY = grid.findIndex(line => line.indexOf('^') !== -1);
   const startX = grid[startY].indexOf('^');
   let currentPosition = new Coordinate(startX,  startY);
   const positions = new Set();
    // Using strings here as equivalent objects are not equal, so Coordinate can't be used
   positions.add(currentPosition.toString());
   let direction = Direction.UP;

    while (nextPositionIsInBounds(currentPosition, direction, grid)) {
        [currentPosition, direction] = findNextPosition(currentPosition, direction, grid);
        positions.add(currentPosition.toString());
    }

   return positions.size;
}

// Find the number of combinations where adding a single # would create a loop
// When we find a visited spot, place obstacle in front
// If next position has been visitied in the same direction, we have a loop
function partTwo(input: string): number {
    
}

function parseInput(input: string): string[][] {
    return parseFile(input).split("\n").map(line => line.split(""));
}

function findNextPosition(currentPosition: Coordinate, direction: Direction, grid: string[][]): [Coordinate, Direction] {
    const nextCoordInSameDirection = currentPosition.combine(directionMap.get(direction)!.coord);
    
    if (grid[nextCoordInSameDirection.y][nextCoordInSameDirection.x] === '#') {
        const newDirection = directionMap.get(direction)!.direction;
        const nextCoordInNewDirection = currentPosition.combine(directionMap.get(newDirection)!.coord);
        
        return [nextCoordInNewDirection, newDirection];
    }
    return [nextCoordInSameDirection, direction];
}

function nextPositionIsInBounds(currentPosition: Coordinate, direction: Direction, array: string[][]) {
    const nextPosition = currentPosition.combine(directionMap.get(direction)?.coord!);
    return !(nextPosition.x < 0 || nextPosition.y < 0 || nextPosition.y > array.length - 1 || nextPosition.x > array[0].length - 1);
}

console.log(partOne("day_6/input.txt")); // 4826