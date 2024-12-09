fun main() {

    fun transformToFileIdList(input: List<String>): MutableList<String> {
        var fileId = 0
        return input[0]
            .mapIndexed { i, it ->
                val isFile = i % 2 == 0
                val list =
                    MutableList<String>(it.digitToInt()) {
                        if (isFile) {
                            fileId.toString()
                        } else {
                            "."
                        }
                    }
                if (isFile) {
                    fileId++
                }
                return@mapIndexed list
            }
            .flatten()
            .toMutableList()
    }

    fun checksum(diskmap: List<String>): Long {
        return diskmap
            .mapIndexed { i, it ->
                if (it == ".") {
                    0
                } else {
                    i.toLong() * it.toLong()
                }
            }
            .sum()
    }

    fun part1(input: List<String>): Long {
        val diskmap = transformToFileIdList(input)

        // compact space (re-order elements)
        var i = 0
        var j = diskmap.size - 1
        while (i < j) {
            if (diskmap[i] != ".") i++
            else if (diskmap[j] == ".") j--
            else {
                val temp = diskmap[i]
                diskmap[i] = diskmap[j]
                diskmap[j] = temp
            }
        }

        return checksum(diskmap)
    }

    fun part2(input: List<String>): Long {
        val diskmap = transformToFileIdList(input)

        // compact space (re-order elements)
        val seenFiles = mutableSetOf<String>()
        var j = diskmap.size - 1
        while (j > 0) {
            if (diskmap[j] == "" || seenFiles.contains(diskmap[j])) {
                j--
            } else {
                seenFiles.add(diskmap[j])

                // get size of j
                var fileSize = 1
                while (j > 0 && diskmap[j - 1] == diskmap[j]) {
                    j--
                    fileSize++
                }

                // look for free space
                var i = 0
                while (i < j) {
                    if (diskmap[i] == ".") {
                        // count free space
                        var freeSpaceSize = 1
                        for (k in i + 1 until j) {
                            if (diskmap[k] == ".") {
                                freeSpaceSize++
                            } else {
                                break
                            }
                        }
                        // swap if enough space
                        if (freeSpaceSize >= fileSize) {
                            var eof = j + fileSize
                            while (j < eof) {
                                diskmap[i++] = diskmap[j]
                                diskmap[j++] = "."
                            }
                            j--
                            break
                        } else {
                            i += freeSpaceSize
                        }
                    } else {
                        i++
                    }
                }
            }
        }

        return checksum(diskmap)
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
