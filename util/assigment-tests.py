from utils import *

TESTS: List[TestCase] = [
    # bytes
    TestCase(args=['-f', 'bytes', '-t', 'bytes'], data_in=b'test', expected_out=b'test', expected_code=0),
    TestCase(args=['-f', 'bytes', '-t', 'bytes'], data_in='čauky'.encode('utf8'), expected_out='čauky'.encode('utf8'), expected_code=0),  # extended test
    TestCase(args=['-f', 'bytes', '-t', 'bytes'], data_in=b'test1\ntest2', expected_out=b'test1\ntest2', expected_code=0),  # extended test
    # hex
    TestCase(args=['-f', 'hex', '-t', 'bytes'], data_in=b'74657374', expected_out=b'test', expected_code=0),
    TestCase(args=['-f', 'bytes', '-t', 'hex'], data_in=b'test', expected_out=b'74657374', expected_code=0),
    TestCase(args=['-f', 'hex', '-t', 'bytes'], data_in=b'74 65 73 74', expected_out=b'test', expected_code=0),
    TestCase(args=['-f', 'bytes', '-t', 'hex', '--delimiter="\n"'], data_in=b'hel\nlo\n', expected_out=b'68656c\n6c6f\n', expected_code=0),
    # int
    TestCase(args=['-f', 'int', '-t', 'hex'], data_in=b'1234567890', expected_out=b'499602d2', expected_code=0),
    TestCase(args=['-f', 'int', '-t', 'hex', '--from-options=big'], data_in=b'1234567890', expected_out=b'499602d2', expected_code=0),
    TestCase(args=['-f', 'int', '-t', 'hex', '--from-options=little'], data_in=b'1234567890', expected_out=b'd2029649', expected_code=0),
    TestCase(args=['-f', 'hex', '-t', 'int'], data_in=b'499602d2', expected_out=b'1234567890', expected_code=0),
    TestCase(args=['-f', 'hex', '-t', 'int', '--to-options=big'], data_in=b'499602d2', expected_out=b'1234567890', expected_code=0),
    TestCase(args=['-f', 'hex', '-t', 'int', '--to-options=little'], data_in=b'd2029649', expected_out=b'1234567890', expected_code=0),
    # bits
    TestCase(args=['-f', 'bits', '-t', 'bytes'], data_in=b'100 1111 0100 1011', expected_out=b'OK', expected_code=0),
    TestCase(args=['-f', 'bits', '-t', 'bytes', '--from-options=left'], data_in=b'100111101001011', expected_out=b'OK', expected_code=0),
    TestCase(args=['-f', 'bits', '-t', 'hex', '--from-options=right'], data_in=b'100111101001011', expected_out=b'9e96', expected_code=0),
    TestCase(args=['-f', 'bytes', '-t', 'bits'], data_in=b'OK', expected_out=b'0100111101001011', expected_code=0),
    # array
    TestCase(
        args=['-f', 'hex', '-t', 'array'],
        data_in=b'01020304',
        expected_out=b'{0x1, 0x2, 0x3, 0x4}'
    , expected_code=0),
    TestCase(
        args=['-f', 'array', '-t', 'hex'],
        data_in=br"{0x01, 2, 0b11, '\x04'}",
        expected_out=b'01020304'
    , expected_code=0),
    TestCase(
        args=['-f', 'array', '-t', 'array', '--to-options=0x'],
        data_in=br"[0x01, 2, 0b11, '\x04']",
        expected_out=b'{0x1, 0x2, 0x3, 0x4}'
    , expected_code=0),
    TestCase(
        args=['-f', 'array', '-t', 'array', '--to-options=0'],
        data_in=br"(0x01, 2, 0b11, '\x04')",
        expected_out=b'{1, 2, 3, 4}'
    , expected_code=0),
    TestCase(
        args=['-f', 'array', '-t', 'array', '--to-options=a'],
        data_in=br"{0x01, 2, 0b11, '\x04'}",
        expected_out=br"{'\x01', '\x02', '\x03', '\x04'}"
    , expected_code=0),
    TestCase(
        args=['-f', 'array', '-t', 'array', '--to-options=0b'],
        data_in=br"[0x01, 2, 0b11, '\x04']",
        expected_out=br"{0b1, 0b10, 0b11, 0b100}"
    , expected_code=0),
    TestCase(
        args=['-f', 'array', '-t', 'array', '--to-options="("'],
        data_in=br"(0x01, 2, 0b11, '\x04')",
        expected_out=br"(0x1, 0x2, 0x3, 0x4)"
    , expected_code=0),
    TestCase(
        args=['-f', 'array', '-t', 'array', '--to-options=0', '--to-options="["'],
        data_in=br"{0x01, 2, 0b11, '\x04'}",
        expected_out=br"[1, 2, 3, 4]"
    , expected_code=0),
    TestCase(
        args=['-f', 'array', '-t', 'array'],
        data_in=br"[[1, 2], [3, 4], [5, 6]]",
        expected_out=br"{{0x1, 0x2}, {0x3, 0x4}, {0x5, 0x6}}"
    , expected_code=0),
    TestCase(
        args=['-f', 'array', '-t', 'array', '--to-options="{"', '--to-options=0'],
        data_in=br"[[1, 2], [3, 4], [5, 6]]",
        expected_out=br"{{1, 2}, {3, 4}, {5, 6}}"
    , expected_code=0),
    TestCase(
        args=['-f', 'array', '-t', 'array', '--to-options=0', '--to-options="["'],
        data_in=br"{{0x01, (2), [3, 0b100, 0x05], '\x06'}}",
        expected_out=br"[[1, [2], [3, 4, 5], 6]]"
    , expected_code=0),
    TestCase(
        args=['-f', 'array', '-t', 'array'],
        data_in=br"()",
        expected_out=br"{}"
    , expected_code=0),
    TestCase(
        args=['-f', 'array', '-t', 'array', '--to-options="["'],
        data_in=br"([],{})",
        expected_out=br"[[], []]"
    , expected_code=0)
]

if __name__ == '__main__':
    exit(main(TESTS))
