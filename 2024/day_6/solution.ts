import { DirectedCoordinate, Direction } from "../interfaces.ts";
import { parseFile } from '../utils.ts';

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

    for (let i = 0; i < positions.length; i++) {
        // Doesn't account for where obstacle occurs right after turning
        const originalPosition = positions[i];
        const grid = parseInput(input);

        let currentPosition = new DirectedCoordinate(originalPosition.x,  originalPosition.y, originalPosition.direction);

        if (!nextPositionIsInBounds(currentPosition, grid)) {
            break;
        }
        const positionAfterBlockAdded = findNextPosition(currentPosition, grid, true);
        const obstacle = findNextPosition(currentPosition, grid)
        const newPositions = new Map<string, number>([
            [currentPosition.toFullString(), 1], 
            [positionAfterBlockAdded.toFullString(), 1]
        ]);
        currentPosition = positionAfterBlockAdded;

        while (nextPositionIsInBounds(currentPosition, grid)) {
            currentPosition = findNextPosition(currentPosition, grid, false, obstacle);
            const currentKey = newPositions.get(currentPosition.toFullString());
            // Below can get stuck as sometimes the first element in the array 
            const loopsBackToStart = currentKey !== undefined && currentKey > 0;
            newPositions.set(currentPosition.toFullString(), (currentKey ?? 0) + 1);
            
            if (loopsBackToStart) {
                const blockInAlreadyTraversedPath = positions.slice(0, i).map(it => it.toString()).includes(obstacle.toString());
                if (!blockInAlreadyTraversedPath) {
                    loopBlockPositions.push(obstacle);
                }
                break;
            }
        }
    }
    const duplicates = loopBlockPositions.map(position => position.toString()).filter((e, i, a) => a.indexOf(e) !== i)

    return new Set(loopBlockPositions.map(position => position.toString())).size;
}

function parseInput(input: string): string[][] {
    return parseFile(input).split("\n").map(line => line.split(""));
}

function findNextPosition(currentPosition: DirectedCoordinate, grid: string[][], forceRotate = false, obstacle: DirectedCoordinate | undefined = undefined): DirectedCoordinate {
    const nextCoordInSameDirection = currentPosition.combineWithDirection(directionMap.get(currentPosition.direction)!, currentPosition.direction);
    
    if (grid[nextCoordInSameDirection.y][nextCoordInSameDirection.x] === '#' || forceRotate) {
        // check new coord isn't also a #
        const newDirection = directionMap.get(currentPosition.direction)!.direction;
        const newCoordinate = directionMap.get(newDirection)!;
        let nextCoordInNewDirection = currentPosition.combineWithDirection(newCoordinate, newDirection);
        while (grid[nextCoordInNewDirection.y][nextCoordInNewDirection.x] === '#' 
            || (obstacle !== undefined && obstacle.sharesCoordinatesWith(nextCoordInNewDirection))) {
            const newDirection = directionMap.get(nextCoordInNewDirection.direction)!.direction;
            const newCoordinate = directionMap.get(newDirection)!;
            nextCoordInNewDirection = currentPosition.combineWithDirection(newCoordinate, newDirection);
        }
        
        return nextCoordInNewDirection;
    }
    return nextCoordInSameDirection;
}

function nextPositionIsInBounds(currentPosition: DirectedCoordinate, array: string[][]) {
    const nextPosition = currentPosition.combineWithDirection(directionMap.get(currentPosition.direction)!, directionMap.get(currentPosition.direction)!.direction);
    return !(nextPosition.x < 0 || nextPosition.y < 0 || nextPosition.y > array.length - 1 || nextPosition.x > array[0].length - 1);
}

// console.log(partOne("day_6/input.txt")); // 4826
console.log(partTwo("day_6/input_test_3.txt")); 
console.log(partTwo("day_6/input_test_2.txt")); 
console.log(partTwo("day_6/input_test.txt")); 
console.log(partTwo("day_6/input.txt")); // 1775 is too high, 1408 too low, not 1435, not 1614