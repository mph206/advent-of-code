import { DirectedCoordinate, Direction } from "../interfaces.ts";
import { parseFile } from '../utils.ts';

const directionMap: Map<Direction, DirectedCoordinate> = new Map([
    [Direction.UP, new DirectedCoordinate(0, -1, Direction.RIGHT)],
    [Direction.RIGHT, new DirectedCoordinate(1, 0, Direction.DOWN)],
    [Direction.DOWN, new DirectedCoordinate(0, 1, Direction.LEFT)],
    [Direction.LEFT, new DirectedCoordinate(-1, 0, Direction.UP)]
]);

function partOne(input: string): number {
   const grid = parseInput(input);
   const startY = grid.findIndex(line => line.indexOf('^') !== -1);
   const startX = grid[startY].indexOf('^');
   let currentPosition = new DirectedCoordinate(startX,  startY, Direction.UP);
   const positions: Array<DirectedCoordinate> = [currentPosition];

    while (nextPositionIsInBounds(currentPosition, grid)) {
        currentPosition = findNextPosition(currentPosition, grid);
        positions.push(currentPosition);
    }

    // Using strings here as equivalent objects are not equal, so Coordinate can't be used
   return new Set(positions.map(position => position.toString())).size
}

// Find the number of combinations where adding a single # would create a loop
// When we find a visited spot, place obstacle in front
// If next position has been visitied in the same direction, we have a loop
function partTwo(input: string): number {
    
}

function parseInput(input: string): string[][] {
    return parseFile(input).split("\n").map(line => line.split(""));
}

function findNextPosition(currentPosition: DirectedCoordinate, grid: string[][]): DirectedCoordinate {
    const nextCoordInSameDirection = currentPosition.combineWithDirection(directionMap.get(currentPosition.direction)!, currentPosition.direction);
    
    if (grid[nextCoordInSameDirection.y][nextCoordInSameDirection.x] === '#') {
        const newDirection = directionMap.get(currentPosition.direction)!.direction;
        const newCoordinate = directionMap.get(newDirection)!;
        const nextCoordInNewDirection = currentPosition.combineWithDirection(newCoordinate, newDirection);
        
        return nextCoordInNewDirection;
    }
    return nextCoordInSameDirection;
}

function nextPositionIsInBounds(currentPosition: DirectedCoordinate, array: string[][]) {

    const nextPosition = currentPosition.combineWithDirection(directionMap.get(currentPosition.direction)!, directionMap.get(currentPosition.direction)!.direction);
    return !(nextPosition.x < 0 || nextPosition.y < 0 || nextPosition.y > array.length - 1 || nextPosition.x > array[0].length - 1);
}

console.log(partOne("day_6/input.txt")); // 4826