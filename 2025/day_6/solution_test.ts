import { assertEquals } from "jsr:@std/assert";
import { partOne, partTwo } from "./solution.ts";

Deno.test("Part one test input", () => {  
    const output = partOne("./day_6/input_test.txt");
    assertEquals(output, 4277556);
});

Deno.test("Part one full input", () => {  
    const output = partOne("./day_6/input.txt");
    assertEquals(output, 7644505810277);
});

Deno.test("Part 2 test input", () => {  
    const output = partTwo("./day_6/input_test.txt");
    assertEquals(output, 3263827);
});

Deno.test("Part 2 full input", () => {  
    const output = partTwo("./day_6/input.txt");
    assertEquals(output, 12841228084455);
});
