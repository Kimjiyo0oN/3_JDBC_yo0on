package edu.kh.emp.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.vo.Employee;

// 화면용 클래스(입력(Scanner) / 출력(print()))
public class EmployeeView {
	private Scanner sc = new Scanner(System.in);
	
	private EmployeeDAO dao = new EmployeeDAO();
	//메인 메뉴 
	public void displayMenu() {
		int input = 0;
		
		do {
			try{
				System.out.println("---------------------------------------------------------");
				System.out.println("----- 사원 관리 프로그램 -----");
				System.out.println("1. 새로운 사원 정보 추가");
				System.out.println("1-2. 새로운 사원 정보 추가(선택번호는 11번)");
				System.out.println("2. 전체 사원 정보 조회");
				System.out.println("3. 사번이 일치하는 사원 정보 조회");
				System.out.println("4. 사번이 일치하는 사원 정보 수정");
				System.out.println("5. 사번이 일치하는 사원 정보 삭제");
				System.out.println("6. 입력 받은 부서와 일치 모든 사원 정보 조회");
				System.out.println("7. 입력 받은 급여 이상을 받는 모든 사원 정보 조회");
				System.out.println("8. 부서별 급여 합 전체 조회");
				System.out.println("9. 주민등록번호가 일치하는 사원 정보 조회");
				System.out.println("10. 직급별 급여 평균 조회");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 >> ");
				input = sc.nextInt();
				
				System.out.println();
				
				 switch(input) {
				 case 11: addEmployee(); break; 
				 case 1: insertEmployee(); break; 
				 case 2: selectAll(); break;
				 case 3: selectEmpId(); break;
				 case 4: updateEmployee(); break;
				 case 5: deleteEmployee(); break;
				 case 6: selectDeptEmp(); break; //selectDepartment();
				 case 7: selectSalaryEmp(); break;
				 case 8: selectDeptTotalSalary(); break;
				 case 9: selectEmpNo(); break;
				 case 10:selectJobAvgSalary(); break;
				 case 0: System.out.println("프로그램을 종료합니다. "); break;
				 default: System.out.println("메뉴에 존재하는 번호만 입력하세요. ");
				 }
			}catch(InputMismatchException e) {
				System.out.println("정수만 입력해주세요");
				input = -1; //반복문 첫 번째 바퀴에서 잘못 입력하면 종료되는 상황을 방지 
				sc.nextLine(); // 입력 버퍼에 남아 있는 잘못 입력된 문자열 제거해서 
							   // 무한 반복 방지 
			}
		}while(input !=0);
	}
	
	private void selectDeptTotalSalary() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		System.out.println("<부서별 급여 합 전체 조회>");
		
		map =dao.selectDeptTotalSalary();
	
		System.out.println("  부서코드     |  급여합  " );
		System.out.println("------------------------------------------------------------------------------------------------");
		for(String s : map.keySet()) { 
			System.out.printf("%-7s|    %d\n",
					s, map.get(s)); 
		}
		
	}

	private void selectSalaryEmp() {
		System.out.println("<입력 받은 급여 이상을 받는 모든 사원 정보 조회>");
		
		System.out.print("급여 입력 : ");
		int salary = sc.nextInt();
		
		List<Employee> empList = dao.selectSalaryEmp(salary);
		
		printAll(empList);
		
	}

	private void selectDeptEmp() {
		System.out.println("<입력 받은 부서와 일치 모든 사원 정보 조회>");
		
		System.out.print("부서 입력 : ");
		String deptTitle = sc.next();
		
		List<Employee> empList = dao.selectDeptEmp(deptTitle);
		
		printAll(empList);
	}

	private void selectJobAvgSalary() {
		Map<String, Double> map = new HashMap<String, Double>();
		System.out.println("<10. 직급별 급여 평균 조회>");
		
		map =dao.selectJobAvgSalary();
	
		System.out.println("  직급명   |   평균급여" );
		System.out.println("------------------------------------------------------------------------------------------------");
		for(String s : map.keySet()) { 
			System.out.printf("  %4s   |    %.1f\n",
					s , map.get(s)); 
		}
		
		
	}
//	private void selectJobAvgSalary() {
//		List<Employee> list = new ArrayList<>();
//		System.out.println("<10. 직급별 급여 평균 조회>");
//		
//		list =dao.selectJobAvgSalary();
//	
//		System.out.println("  직급명   |   평균급여" );
//		System.out.println("------------------------------------------------------------------------------------------------");
//		for(Employee emp : list) { 
//			System.out.printf("  %4s   |    %.1d\n",
//					emp.getJobName(), emp.getSalary()); 
//		}
//		
//		
//	}
	/**
	 * 사원 정보 추가 새로운 방법으로 시도 해보고 싶음
	 */
	public void addEmployee() {
		System.out.println("<1-2. 새로운 사원 정보 추가>");
	
		int empId = inputEmpId();
	
		System.out.print("직원 이름 : ");
		String empName = sc.next();
	
		System.out.print("직원 주민 등록번호 : ");
		String empNo = sc.next();
	
		System.out.print("직원 이메일 : ");
		String email = sc.next();
	
		System.out.print("직원 전화번호(-제외) : ");
		String phone = sc.next();
	
		System.out.print("부서명 : ");
		String departmentTitle = sc.next();
	
		System.out.print("직급명 : ");
		String jobName  = sc.next();
		System.out.print("급여 : ");
		int salary  = sc.nextInt();
		
		System.out.print("보너스 : ");
		double bonus  = sc.nextDouble();
		
		System.out.print("사수번호 : ");
		int managerid  = sc.nextInt();
	
		Employee emp = new Employee(empId, empName, empNo, email, phone, departmentTitle, jobName, salary, bonus, managerid);
		int result = dao.addEmployee(emp);
		// INSERT, UPDATE, DELETE 같은 DML 구문은
		// 수행 후 테이블에 반영된 행의 개수를 반환함
		// -> 조건이 잘못된 경우 반영된 행이 없으므로 0 반환
		if(result > 0) { // DML 구문 성공 시
			System.out.println( result +"명 회원 정보 추가 성공");
		}else if(result == -1){
			System.out.println("입력하신 회원의 사번이 존재합니다.");
		}else {
			System.out.println("회원 정보 추가 실패");
		}
	
	}
	/**
	 * 사원 정보 추가 
	 */
	public void insertEmployee() {
		System.out.println("<새로운 사원 정eat보 추가>");
	
		int empId = inputEmpId();
	
		System.out.print("직원 이름 : ");
		String empName = sc.next();
	
		System.out.print("직원 주민 등록번호 : ");
		String empNo = sc.next();
	
		System.out.print("직원 이메일 : ");
		String email = sc.next();
	
		System.out.print("직원 전화번호(-제외) : ");
		String phone = sc.next();
	
		System.out.print("부서코드(D1~D9) : ");
		String deptCode = sc.next();
	
		System.out.print("직급코드(J1~J7) : ");
		String jobCode  = sc.next();
	
		System.out.print("급여등급(S1~S6) : ");
		String salLevel  = sc.next();
		
		System.out.print("급여 : ");
		int salary  = sc.nextInt();
		
		System.out.print("보너스 : ");
		double bonus  = sc.nextDouble();
		
		System.out.print("사수번호 : ");
		int managerid  = sc.nextInt();
	
		
		Employee emp = new Employee(empId, empName, empNo, email, phone, 
				salary, deptCode, salLevel, jobCode, bonus, managerid);
		
		int result = dao.insertEmployee(emp);
		// INSERT, UPDATE, DELETE 같은 DML 구문은
		// 수행 후 테이블에 반영된 행의 개수를 반환함
		// -> 조건이 잘못된 경우 반영된 행이 없으므로 0 반환
		if(result > 0) { // DML 구문 성공 시
			System.out.println( result +"명 회원 정보 추가 성공");
		}else {
			System.out.println("회원 정보 추가 실패");
		}
	
	}
	
	/**
	 * 전체 사원 조회
	 */
	public void selectAll() {
		System.out.println("<전체 사원 정보 조회>");
		
		// DB에서 전체 사원 정보를 조회하여 List<Employee> 형태로 반환하는
		// dao.selectAll() 메서드 호출
		List<Employee> empList = dao.selectAll();
		
		printAll(empList);
	}
	
	/**전달 받은 사원 list 모두 출력
	 * @param empList
	 */
	public void printAll(List<Employee> empList) {
		if(empList.isEmpty()) {
			System.out.println("조회된 사원 정보가 없습니다.");
			
		} else {
			System.out.println("사번 |   이름  | 주민 등록 번호 |        이메일        |   전화 번호   | 부서 | 직책 | 급여" );
			System.out.println("------------------------------------------------------------------------------------------------");
			for(Employee emp : empList) { 
				System.out.printf(" %2d  | %4s | %s | %20s | %s | %s | %s | %d\n",
						emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(), 
						emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
			}
		}
			
	}
	/** 사원 1명 정보 출력 
	 * @param emp
	 */
	public void printOne(Employee emp) {
		if(emp == null) {
			System.out.println("조회된 사원 정보가 없습니다.");
		} else {
			System.out.println("사번 |   이름  | 주민 등록 번호 |        이메일        |   전화 번호   | 부서 | 직책 | 급여" );
			System.out.println("------------------------------------------------------------------------------------------------");
			System.out.printf(" %2d  | %4s | %s | %20s | %s | %s | %s | %d\n",
					emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(), 
					emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
		}
	}
	
	/**
	 * 사번이 일치하는 사원 정보 조회
	 */
	public void selectEmpId() {
		System.out.println("<사번이 일치하는 사원 정보 조회>");
		
		//사번 입력 받기
		int empId = inputEmpId();
		
		// 입력 받은 사번을 DAO의 selectEmpId() 메서드로 전달하여 
		// 조회된 사원 정보 반환 받기 
		Employee emp = dao.selectEmpId(empId);
		
		printOne(emp); // 조회 결과 출력 
	}
	
	/** 사번을 입력 받아 반환하는 메서드
	 * @return
	 */
	public int inputEmpId() {
		System.out.print("사번 입력 : ");
		int empId = sc.nextInt();
		sc.nextLine();
		
		return empId;
	}
	
	public void selectEmpNo() {
		System.out.println("<주민등록번호가 일치하는 사원 정보 조회>");
		
		System.out.print("주민번호 입력 : ");
		String empNo = sc.next();
		sc.nextLine();
		
		Employee emp = dao.selectEmpNo(empNo);
		
		printOne(emp);
		
	}
	/**
	 * 사번이 일치하는 사원 정보 수정(이메일, 전화번호, 급여)
	 */
	public void updateEmployee() {
		System.out.println("<사번이 일치하는 사원 정보 수정>");
		
		int empId = inputEmpId(); //사번 입력 
		
		System.out.print("이메일 : ");
		String email = sc.next();
		
		System.out.print("전화번호(- 제외) : ");
		String phone = sc.next();
		
		System.out.print("급여 : ");
		int salary = sc.nextInt();
		
		Employee emp = new Employee();
		emp.setEmpId(empId);
		emp.setEmail(email);
		emp.setPhone(phone);
		emp.setSalary(salary);
		
		int result = dao.updateEmployee(emp); //UPDATE(DML) -> 반영된 행의 개수 반환(int형)
		
		if(result > 0) {
			System.out.println("사원 정보가 수정되었습니다.");
		}else {
			System.out.println("사원이 일치하는 사원이 존재하지 않습니다.");
		}
	}
	/**
	 * 사번이 일치하는 사원 정보 삭제
	 */
	public void deleteEmployee() {
		System.out.println("<사번이 일치하는 사원 정보 삭제>");
		
		int empid = inputEmpId(); //사번 입력 
		
		System.out.print("정말 삭제하시겠습니까(Y/N)? ");
		char input = sc.next().toUpperCase().charAt(0); // Y/N 대소문자 구분 없이 입력
														// -> 모두 대문자로 변환
		if(input == 'Y') {
			//삭제를 수행하는 DAO 호출
			//성공 : "삭제되었습니다"
			//실패 : "사번이 일치하는 사원이 존재하지 않습니다 " 출력 
			int result = dao.deleteEmployee(empid);
			if(result >0 ) {
				System.out.println("삭제되었습니다.");
			}else {
				System.out.println("사번이 일치하는 사원이 존재하지 않습니다.");
			}
		}else {
			System.out.println("최소되었습니다.");
		}
		
	}
}
