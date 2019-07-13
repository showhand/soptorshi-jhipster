export interface IDepartmentHead {
    id?: number;
    officeName?: string;
    officeId?: number;
    departmentName?: string;
    departmentId?: number;
    employeeFullName?: string;
    employeeId?: number;
}

export class DepartmentHead implements IDepartmentHead {
    constructor(
        public id?: number,
        public officeName?: string,
        public officeId?: number,
        public departmentName?: string,
        public departmentId?: number,
        public employeeFullName?: string,
        public employeeId?: number
    ) {}
}
