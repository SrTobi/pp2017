#include <stdio.h>
#include <mpi.h>

void my_int_sum_reduce(int *sendbuf, int *recvbuf, int count,
                        int root, MPI_Comm comm)
{
    int size, rank;
    MPI_Comm_size(comm, &size);
    MPI_Comm_rank(comm, &rank);
    
    if (rank == root) {
        int tempbuf[count];

        for (int j = 0; j < count; ++j) {
            recvbuf[j] = sendbuf[j];
        }
        
        for (int i = 0; i < size; ++i) {
            if (i != root) {
                MPI_Recv(tempbuf, count, MPI_INT, i, 0, comm, MPI_STATUS_IGNORE);

                for (int j = 0; j < count; ++j) {
                    recvbuf[j] += tempbuf[j];
                }
            }
        }
    } else {
        MPI_Send(sendbuf, count, MPI_INT, root, 0, comm);
    }
}

int main(int argc, char** argv) {
    MPI_Init(&argc, &argv);
    int size, rank;
    MPI_Comm_size(MPI_COMM_WORLD, &size);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    int sendbuffer[4];
    int recvbuffer[4];

    for (int i = 0; i < 4; i++) {
        sendbuffer[i] = rank + i;
    }

    //MPI_Reduce(sendbuffer, recvbuffer, 4, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);
    my_int_sum_reduce(sendbuffer, recvbuffer, 4, 0, MPI_COMM_WORLD);

    for (int i = 0; i < 4; ++i) {
        if (i == rank) {
            printf("sendbuf %i | ", rank);
            for (int j = 0; j < 4; ++j) {
                printf("%2i ", sendbuffer[j]);
            }
            printf("\n");
        }
    }

    if (rank == 0) {
        printf("recvbuf 0 | ");
        for (int j = 0; j < 4; ++j) {
            printf("%2i ", recvbuffer[j]);
        }
        printf("\n");
    }

    MPI_Finalize();
    return 0;
}