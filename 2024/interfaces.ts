class Coordinate {
    x: number;
    y: number;

    constructor(x: number, y: number) {
      this.x = x;
      this.y = y;
    }

    combine(other: Coordinate): Coordinate {
      return new Coordinate(this.x + other.x, this.y + other.y);
    }

    toString(): string {
      return `{x: ${this.x}, y: ${this.y}}`
    }
}

enum Direction {
  UP, RIGHT, DOWN, LEFT
}

export {
  Direction,
  Coordinate
}