#include <mpi.h>
#include <stdio.h>

#define n 2

void mMult(int root, int a[n][n], int b[n][n], int c[n][n])
{
    int procs;
    int rank;
    MPI_Comm_size(MPI_COMM_WORLD, &procs);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    MPI_Bcast(a, n * n, MPI_INT, root, MPI_COMM_WORLD);

    const int columnPerProcess = n / procs;
    const int intsPerProcess = columnPerProcess * n;
    int bColumns[columnPerProcess][n];
    MPI_Scatter(b, intsPerProcess, MPI_INT,
                bColumns, intsPerProcess, MPI_INT,
                root, MPI_COMM_WORLD);
    
    for (int i = 0; i < columnPerProcess; ++i) {
        for (int arow = 0; arow < n; ++arow) {
            int result = 0;
            for (int col = 0; col < n; ++col) {
                result += a[col][arow] * bColumns[i][col];
            }
            int cColumn = rank * columnPerProcess + i;
            c[cColumn][arow] = result;
        }
    }
    MPI_Allgather(c[intsPerProcess * rank], intsPerProcess, MPI_INT, c, intsPerProcess, MPI_INT, MPI_COMM_WORLD);
}


int main(int argc, char** argv) {
    MPI_Init(&argc, &argv);
    
    int a[n][n] = {
        { 1, 2},
        { 3, 4}
    };

    int b[n][n] = {
        { 1, 0},
        { 0, 1}
    };

    int c[n][n];

    mMult(0, a, b, c);

    int procs;
    int rank;
    MPI_Comm_size(MPI_COMM_WORLD, &procs);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    if (rank == 0) {
        for (int col = 0; col < n; col++) {
            for (int row = 0; row < n; row++) {
                printf("%i ", c[col][row]);
            }
            printf("\n");
        }
    }

    MPI_Finalize();
    return 0;
}