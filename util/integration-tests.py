from utils import *

TESTS: List[TestCase] = [
    # Error arguments
    TestCase(
        args=['-help'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'arra', '-t', 'hex'],
        data_in=br"()",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'array', '-', 'array'],
        data_in=br"([])",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['--from array', '-', 'array'],
        data_in=br"([])",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'array', '--to-array'],
        data_in=br"([])",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bytes '],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-t', 'bytes '],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bytes ', '-t', 'hex'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bytes', '-t', 'hex', '--input-FILE'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bytes', '-t', 'hex', '--output-FILE'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=[' -f', 'bytes', '-t', 'hex'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bytes', '-t', 'hex', '--output-FILE'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    # invalid argument combinations
    TestCase(
        args=['-f', 'bytes', '-t', 'hex', '--to-options=big'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'hex', '-t', 'hex', '--from-options=big'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bytes', '-t', 'int', '--to-options=left'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'int', '-t', 'int', '--from-options=left'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'int', '-t', 'int', '--to-options=left'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'int', '--from-options=big'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'bits', '--to-options=little'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'bits', '--to-options=")"'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'bits', '--to-options=a'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'array', '--to-options= a'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'array', '--to-options= a', '--to-options=a'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    # Error inputs
    TestCase(
        args=['-f', 'hex', '-t', 'bytes'],
        data_in=br"ga",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'hex', '-t', 'bytes'],
        data_in=br"ab10 g",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'int', '-t', 'bytes'],
        data_in=br"abc",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'int', '-t', 'bytes'],
        data_in=br"10a",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'int', '-t', 'bytes'],
        data_in=br"10a2",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'bytes'],
        data_in=br"abc",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'bytes'],
        data_in=br"1110O1",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'bytes'],
        data_in=br"11101111O",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'array', '-t', 'bytes'],
        data_in=br"abc",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'array', '-t', 'hex'],
        data_in=br"(",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'array', '-t', 'hex'],
        data_in=br"(a, }",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'array', '-t', 'hex'],
        data_in=br"(a, )",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'array', '-t', 'array'],
        data_in=br"(a, )",
        expected_out=None,
        expected_code=1
    ),
    # Correct inputs & arguments
    TestCase(
        args=['-f', 'bits', '-t', 'bits', '--from-options=left', '--from-options=right'],
        data_in=br"",
        expected_out=br"",
        expected_code=0
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'array', '--to-options=a', '--to-options=a'],
        data_in=br"",
        expected_out=br"{}",
        expected_code=0
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'array', '--to-options=a', '--to-options="("'],
        data_in=br"",
        expected_out=br"()",
        expected_code=0
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'array', '--to-options=a', '--to-options="]"'],
        data_in=br"",
        expected_out=br"[]",
        expected_code=0
    ),
]

if __name__ == '__main__':
    exit(main(TESTS))
