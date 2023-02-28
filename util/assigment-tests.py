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
    TestCase(args=['-f', 'bytes', '-t', 'bytes'], data_in=b'test', expected_out=b'out')
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
    path_jar = Path('target/panbytes-1.0-SNAPSHOT.jar')
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
