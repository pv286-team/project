from utils import *

TESTS: List[TestCase] = [
    # Error arguments
    TestCase(
        args=[],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
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
        args=['-f', 'int', '-t', 'int', '--to-options=little', '--to-options=left'],
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
        args=['-f', 'bits', '-t', 'bits', '--to-options=big', '--to-options=little'],
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
    TestCase(
        args=['-f', 'bits', '-t', 'array', '--to-options= "(\''],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'array', '--to-options="[]"', '--to-options=big'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'array', '--to-options=a', '--to-options=big'],
        data_in=br"",
        expected_out=None,
        expected_code=1
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'array', '--input=nonexistent_file'],
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
    TestCase(
        args=['-f', 'array', '-t', 'array', '--delimiter={'],
        data_in=br"{ 1, 2, 3}",
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
    TestCase(
        args=['-f', 'array', '-t', 'hex'],
        data_in=br"{0x01, 0x02}",
        expected_out=b'0102'
        , expected_code=0),
    TestCase(
        args=['-f', 'array', '-t', 'hex'],
        data_in=br"((1, 2), (3, 4))",
        expected_out=b'01020304'
        , expected_code=0),
    TestCase(
        args=['-f', 'array', '-t', 'array'],
        data_in=br"{0x01,2,0b11 ,'\x04' }",
        expected_out=b'{0x1, 0x2, 0x3, 0x4}'
        , expected_code=0),
    TestCase(
        args=['-f', 'array', '-t', 'array'],
        data_in=br"{(),  {{}, {[]}} ,{   }}",
        expected_out=b'{{}, {{}, {{}}}, {}}'
        , expected_code=0),
    TestCase(
        args=['-f', 'int', '-t', 'int', '--delimiter="0"'],
        data_in=br"1020999002",
        expected_out=br"1020999002",
        expected_code=0
    ),
    TestCase(
        args=['-f', 'int', '-t', 'hex', '--delimiter="0"'],
        data_in=br"0110120",
        expected_out=br"00b00c0",
        expected_code=0
    ),
    TestCase(
        args=['-f', 'hex', '-t', 'hex', '--delimiter="0"'],
        data_in=br"0aa0bb0cccc00ff",
        expected_out=br"0aa0bb0cccc00ff",
        expected_code=0
    ),
    TestCase(
        args=['-f', 'bits', '-t', 'bits', '--delimiter="0"'],
        data_in=br"01011001",
        expected_out=br"0000000010000000110000000001",
        expected_code=0
    ),

]

if __name__ == '__main__':
    exit(main(TESTS))
