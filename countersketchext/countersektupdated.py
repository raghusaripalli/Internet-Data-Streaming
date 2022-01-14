import random
import statistics as st
import sys


def counterUpdate(k):
    k1 = k
    k2 = k
    w = 300
    numFlows = 0
    max31 = (2 ** 32) - 1

    # sys.stdout  =  open("CounterSketchVariationPython.txt","w")
    hashes1 = []
    for i in range(k1):
        hashes1.append(random.randint(1, sys.maxsize))
    # hashes1 = random.sample(range(0, max31), k1)
    # hashes2 = random.sample(range(0, max31), k2)
    counters = [[[0 for _ in range(w)] for __ in range(k1)] for ___ in range(k2)]

    f = open("input.txt", "r")

    flag = True

    ipMap = []
    randomValSet = set()
    ips = {}
    for line in f:
        if flag == True:
            numFlows = int(line)
            flag = False
        else:
            line = line.split()
            if line[0] not in ips:
                someRandom = random.randint(0, max31)
                while (someRandom in randomValSet):
                    someRandom = random.randint(0, max31)
                ips[line[0]] = someRandom
                ipMap.append([line[0], someRandom, int(line[1])])
            else:
                someRandom = ips[line[0]]
            for c1 in range(k1):
                # for c2 in range(k2):
                hash = (someRandom ^ hashes1[c1])  # ^ hashes2[c2]
                location1 = hash % w
                location2 = someRandom % k2
                if (hash >> 31) & 1 == 1:
                    counters[location2][c1][location1] -= int(line[1])
                else:
                    counters[location2][c1][location1] += int(line[1])

    error = 0
    for ip in ipMap:
        expected = []
        for c1 in range(k1):
            # for c2 in range(k2):
            hash = (ip[1] ^ hashes1[c1])  # ^ hashes2[c2]
            location1 = hash % w
            location2 = ip[1] % k2
            if (hash >> 31) & 1 == 1:
                expected.append(-counters[location2][c1][location1])
            else:
                expected.append(counters[location2][c1][location1])
        expected.sort()
        median = st.median(expected)

        error += abs(median - ip[2])
        ip.append(median)

    ress = (int(error / len(ipMap)))
    return ress
    # ipMap.sort(key = lambda x: -x[3])
    # for i in range(100):
    #   print(ipMap[i][0], ipMap[i][3], ipMap[i][2])


for i in range(1, 30, 2):
    print(counterUpdate(i))
