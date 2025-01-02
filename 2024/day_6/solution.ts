import { DirectedCoordinate, Direction } from "../utils/interfaces.ts";
import { parseFile } from '../utils/file_parsing.ts';

const directionMap: Map<Direction, DirectedCoordinate> = new Map([
    [Direction.UP, new DirectedCoordinate(0, -1, Direction.RIGHT)],
    [Direction.RIGHT, new DirectedCoordinate(1, 0, Direction.DOWN)],
    [Direction.DOWN, new DirectedCoordinate(0, 1, Direction.LEFT)],
    [Direction.LEFT, new DirectedCoordinate(-1, 0, Direction.UP)]
]);

function partOne(input: string) : number {
    const positions = findPositions(input);
    // Using strings here as equivalent objects are not equal, so Coordinate can't be used
    return new Set(positions.map(position => position.toString())).size
}

function findPositions(input: string): Array<DirectedCoordinate> {
   const grid = parseInput(input);
   const startY = grid.findIndex(line => line.indexOf('^') !== -1);
   const startX = grid[startY].indexOf('^');
   let currentPosition = new DirectedCoordinate(startX,  startY, Direction.UP);
   const positions: Array<DirectedCoordinate> = [currentPosition];

    while (nextPositionIsInBounds(currentPosition, grid)) {
        currentPosition = findNextPosition(currentPosition, grid);
        positions.push(currentPosition);
    }

   return positions
}

function partTwo(input: string): number {
    const positions = findPositions(input);
    const loopBlockPositions = new Array<DirectedCoordinate>();

    for (let i = 0; i < positions.length - 1; i++) {
        const originalPosition = positions[i];
        const grid = parseInput(input);

        let currentPosition = new DirectedCoordinate(originalPosition.x, originalPosition.y, originalPosition.direction);

        const obstacle = findNextPosition(currentPosition, grid)
        grid[obstacle.y].splice(obstacle.x, 1, '#')
        const newPositions = new Map<string, number>([
            [currentPosition.toFullString(), 1], 
        ]);

        while (nextPositionIsInBounds(currentPosition, grid)) {
            currentPosition = findNextPosition(currentPosition, grid);
            const coordVisitCount = newPositions.get(currentPosition.toFullString());
            const loopsBackToStart = coordVisitCount !== undefined && coordVisitCount > 0;
            newPositions.set(currentPosition.toFullString(), (coordVisitCount ?? 0) + 1);
            
            if (loopsBackToStart) {
                const blockInAlreadyTraversedPath = positions.slice(0, i).map(it => it.toString()).includes(obstacle.toString());
                if (!blockInAlreadyTraversedPath) {
                    loopBlockPositions.push(obstacle);
                }
                break;
            }
        }
    }

    return new Set(loopBlockPositions.map(position => position.toString())).size;
}

function parseInput(input: string): string[][] {
    return input.split("\n").map(line => line.trim().split(""));
}

function findNextPosition(currentPosition: DirectedCoordinate, grid: string[][]): DirectedCoordinate {
    const nextCoordInSameDirection = currentPosition.combineWithDirection(directionMap.get(currentPosition.direction)!, currentPosition.direction);
    
    if (grid[nextCoordInSameDirection.y][nextCoordInSameDirection.x] === '#') {
        const newDirection = directionMap.get(currentPosition.direction)!.direction;
        const nextCoordInNewDirection = currentPosition.changeDirection(newDirection);
        
        return nextCoordInNewDirection;
    }
    return nextCoordInSameDirection;
}

function nextPositionIsInBounds(currentPosition: DirectedCoordinate, array: string[][]) {
    const nextPosition = currentPosition.combineWithDirection(directionMap.get(currentPosition.direction)!, directionMap.get(currentPosition.direction)!.direction);
    return !(nextPosition.x < 0 || nextPosition.y < 0 || nextPosition.y > array.length - 1 || nextPosition.x > array[0].length - 1);
}

console.log(partOne(parseFile("day_6/input.txt"))); // 4826
console.log(partTwo(parseFile("day_6/input.txt"))); // 1721

export {
    partTwo
}
