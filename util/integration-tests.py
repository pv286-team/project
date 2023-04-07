from utils import *

TESTS: List[TestCase] = [
    TestCase(
        args=['-f', 'array', '-t', 'hex'],
        data_in=br"(",
        expected_out=None,
        expected_code=1
    )
]

if __name__ == '__main__':
    exit(main(TESTS))
