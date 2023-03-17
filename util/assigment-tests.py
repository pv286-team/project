from subprocess import check_call, PIPE, Popen
from os import chdir
from sys import argv
from pathlib import Path
from typing import List, Tuple, NamedTuple


class TestCase(NamedTuple):
    args: List[str]
    data_in: bytes
    expected_out: bytes



TESTS: List[TestCase] = [
    # bytes
    TestCase(args=['-f', 'bytes', '-t', 'bytes'], data_in=b'test', expected_out=b'test'),
    TestCase(args=['-f', 'bytes', '-t', 'bytes'], data_in='čauky'.encode('utf8'), expected_out='čauky'.encode('utf8')),  # extended test
    TestCase(args=['-f', 'bytes', '-t', 'bytes'], data_in=b'test1\ntest2', expected_out=b'test1\ntest2'),  # extended test
    # hex
    TestCase(args=['-f', 'hex', '-t', 'bytes'], data_in=b'74657374', expected_out=b'test'),
    TestCase(args=['-f', 'bytes', '-t', 'hex'], data_in=b'test', expected_out=b'74657374'),
    TestCase(args=['-f', 'hex', '-t', 'bytes'], data_in=b'74 65 73 74', expected_out=b'test'),
    # int
    # TestCase(args=['-f', 'int', '-t', 'hex'], data_in=b'1234567890', expected_out=b'499602d2'),
    # TestCase(args=['-f', 'int', '-t', 'hex', '--from-options=big'], data_in=b'1234567890', expected_out=b'499602d2'),
    # TestCase(args=['-f', 'int', '-t', 'hex', '--from-options=little'], data_in=b'1234567890', expected_out=b'd2029649'),
    # TestCase(args=['-f', 'hex', '-t', 'int'], data_in=b'499602d2', expected_out=b'1234567890'),
    # TestCase(args=['-f', 'hex', '-t', 'int', '--to-options=big'], data_in=b'499602d2', expected_out=b'1234567890'),
    # TestCase(args=['-f', 'hex', '-t', 'int', '--to-options=little'], data_in=b'd2029649', expected_out=b'1234567890'),
    # bits
    TestCase(args=['-f', 'bits', '-t', 'bytes'], data_in=b'100 1111 0100 1011', expected_out=b'OK'),
    TestCase(args=['-f', 'bits', '-t', 'bytes', '--from-options=left'], data_in=b'100111101001011', expected_out=b'OK'),
    TestCase(args=['-f', 'bits', '-t', 'hex', '--from-options=right'], data_in=b'100111101001011', expected_out=b'9e96'),
    TestCase(args=['-f', 'bytes', '-t', 'bits'], data_in=b'OK', expected_out=b'0100111101001011'),
    # array
    # TestCase(
    #     args=['-f', 'hex', '-t', 'array'],
    #     data_in=b'01020304',
    #     expected_out=b'{0x1, 0x2, 0x3, 0x4}'
    # ),
    # TestCase(
    #     args=['-f', 'array', '-t', 'hex'],
    #     data_in=br"{0x01, 2, 0b11, '\x04'}",
    #     expected_out=b'01020304'
    # ),
    # TestCase(
    #     args=['-f', 'array', '-t', 'array'],
    #     data_in=br"{0x01,2,0b11 ,'\x04' }",
    #     expected_out=b'{0x1, 0x2, 0x3, 0x4}'
    # ),
    # TestCase(
    #     args=['-f', 'array', '-t', 'array', '--to-options=0x'],
    #     data_in=br"[0x01, 2, 0b11, '\x04']",
    #     expected_out=b'{0x1, 0x2, 0x3, 0x4}'
    # ),
    # TestCase(
    #     args=['-f', 'array', '-t', 'array', '--to-options=0'],
    #     data_in=br"(0x01, 2, 0b11, '\x04')",
    #     expected_out=b'{1, 2, 3, 4}'
    # ),
    # TestCase(
    #     args=['-f', 'array', '-t', 'array', '--to-options=a'],
    #     data_in=br"{0x01, 2, 0b11, '\x04'}",
    #     expected_out=br"{'\x01', '\x02', '\x03', '\x04'}"
    # ),
    # TestCase(
    #     args=['-f', 'array', '-t', 'array', '--to-options=0b'],
    #     data_in=br"[0x01, 2, 0b11, '\x04']",
    #     expected_out=br"{0b1, 0b10, 0b11, 0b100}"
    # ),
    # TestCase(
    #     args=['-f', 'array', '-t', 'array', '--to-options="("'],
    #     data_in=br"(0x01, 2, 0b11, '\x04')",
    #     expected_out=br"(0x1, 0x2, 0x3, 0x4)"
    # ),
    # TestCase(
    #     args=['-f', 'array', '-t', 'array', '--to-options=0', '--to-options="["'],
    #     data_in=br"{0x01, 2, 0b11, '\x04'}",
    #     expected_out=br"[1, 2, 3, 4]"
    # ),
    # TestCase(
    #     args=['-f', 'array', '-t', 'array'],
    #     data_in=br"[[1, 2], [3, 4], [5, 6]]",
    #     expected_out=br"{{0x1, 0x2}, {0x3, 0x4}, {0x5, 0x6}}"
    # ),
    # TestCase(
    #     args=['-f', 'array', '-t', 'array', '--to-options="{"', '--to-options=0'],
    #     data_in=br"[[1, 2], [3, 4], [5, 6]]",
    #     expected_out=br"{{1, 2}, {3, 4}, {5, 6}}"
    # ),
    # TestCase(
    #     args=['-f', 'array', '-t', 'array', '--to-options=0', '--to-options="["'],
    #     data_in=br"{{0x01, (2), [3, 0b100, 0x05], '\x06'}}",
    #     expected_out=br"[[1, [2], [3, 4, 5], 6]]"
    # ),
    # TestCase(
    #     args=['-f', 'array', '-t', 'array'],
    #     data_in=br"()",
    #     expected_out=br"{}"
    # ),
    # TestCase(
    #     args=['-f', 'array', '-t', 'array', '--to-options="["'],
    #     data_in=br"([],{})",
    #     expected_out=br"[[], []]"
    # ),
]


def chdir_to_project_root() -> None:
    """
    Changes current working directory to the project root
    :return: None
    """
    project_root = Path(__file__).parent.parent.resolve()
    assert project_root.joinpath('pom.xml').exists()
    chdir(project_root)


def build_jar(force_refresh: bool = False) -> Path:
    """
    Builds application as jar and returns the file
    :param force_refresh: if set to False then does not rebuild if jar already exists
    :return: Path to the build jar file
    """
    path_jar = Path('target/panbyte-1.0-SNAPSHOT.jar')
    if path_jar.exists() and not force_refresh:
        return path_jar

    check_call(['mvn', 'clean', 'package'], stdin=PIPE, timeout=300)
    assert path_jar.exists()
    return path_jar


def execute_single_test(path_jar: Path, args: List[str], data_in: bytes) -> Tuple[bytes, bytes]:
    """
    Executes a single test scenario
    :param path_jar: path to the jar file
    :param args: argumments passed to the program
    :param data_in: stdin
    :return: stdout, stderr
    """
    command = ['java', '-jar', f"{path_jar}"] + args
    process = Popen(command, stdin=PIPE, stdout=PIPE, stderr=PIPE)
    stdout, stderr = process.communicate(input=data_in, timeout=300)

    return stdout, stderr


def main() -> int:
    chdir_to_project_root()

    build_only = False
    if len(argv) >=2 and argv[1] == '--build':
        build_only = True

    path_jar = build_jar(build_only)
    if build_only:
        return 0

    count_passed = 0

    for i, case in enumerate(TESTS):
        print(f'TEST {i+1}/{len(TESTS)} ... ', end='', flush=True)
        stdout, stderr = execute_single_test(path_jar, case.args, case.data_in)
        if stdout == case.expected_out:
            count_passed += 1
            print('OK')
        else:
            print('FAIL!')
            print(f'- expected: {case.expected_out}')
            print(f'- received: {stdout}')
            if stderr:
                print(f'- stderr:   {stderr}')

    print(f'Passed {count_passed}/{len(TESTS)} tests')
    all_ok = count_passed == len(TESTS)
    if not all_ok:
        print('FAIL')
    return 0 if all_ok else 42


if __name__ == '__main__':
    exit(main())
