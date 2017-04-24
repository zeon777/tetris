/**
 * Created by zeon on 13.04.2017.
 */
 class FigureGenerator {
    public static final int[][][] BRICKS = {{
            {1, 1, 0,0},                          //   X X
            {0, 1, 1,0},                          //     X X
            {0, 0, 0,0},
            {0, 0, 0,0}}, {                       //

            {2, 0, 0,0},                          //   X
            {2, 2, 0,0},                          //   X X
            {0, 2, 0,0},                          //     X
            {0, 0, 0,0}}, {

            {0, 3, 0,0},                          //   X
            {0, 3, 0,0},                          //   X
            {0, 3, 0,0},                          //   X
            {0, 3, 0,0}}, {                       //   X

            {6, 6, 0,0},                          //   X X
            {6, 6, 0,0},                          //   X X
            {0, 0, 0,0},
            {0, 0, 0,0}}, {                       //

            {4, 4, 4,0},                          //   X X X
            {0, 4, 0,0},                          //     X
            {0, 0, 0,0},
            {0, 0, 0,0}}, {                       //

            {5, 5, 5,0},                          //   X X X
            {0, 0, 5,0},                          //       X
            {0, 0, 0,0},
            {0, 0, 0,0}},                          //
    };

    /**
     * Метод выбирает случайный шаблон и создает с ним новую фигурку.
     */
    public static Figure createRandomFigure(int x, int y) {
        int index = (int) (Math.random() * 6);
        return new Figure(BRICKS[index], x, y);
    }
}
