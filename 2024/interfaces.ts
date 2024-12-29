class Coordinate {
    x: number;
    y: number;

    constructor(x: number, y: number) {
      this.x = x;
      this.y = y;
    }

    add(other: Coordinate): Coordinate {
      return new Coordinate(this.x + other.x, this.y + other.y);
    }

    subtract(other: Coordinate): Coordinate {
      return new Coordinate(this.x - other.x, this.y - other.y);
    }

    equals(other: Coordinate) {
      return this.x === other.x && this.y === other.y;
    }

    toString(): string {
      return `{x: ${this.x}, y: ${this.y}}`
    }
}

class DirectedCoordinate extends Coordinate {
  direction: Direction;

  constructor(x: number, y: number, direction: Direction) {
    super(x, y);
    this.direction = direction;
  }

  combineWithDirection(other: DirectedCoordinate, direction: Direction): DirectedCoordinate {
    return new DirectedCoordinate(this.x + other.x, this.y + other.y, direction);
  }

  sharesCoordinatesWith(other: DirectedCoordinate) {
    return this.x === other.x && this.y === other.y;
  }

  override equals(other: DirectedCoordinate) {
    return this.x === other.x && this.y === other.y && this.direction === other.direction;
  }

  toFullString(): string {
    return `{x: ${this.x}, y: ${this.y}, direction: ${this.direction}}`
  }
}

enum Direction {
  UP, RIGHT, DOWN, LEFT
}

export {
  Direction,
  DirectedCoordinate,
  Coordinate
}