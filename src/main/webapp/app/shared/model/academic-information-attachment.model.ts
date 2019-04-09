export interface IAcademicInformationAttachment {
    id?: number;
    fileContentType?: string;
    file?: any;
    employeeEmployeeId?: string;
    employeeId?: number;
}

export class AcademicInformationAttachment implements IAcademicInformationAttachment {
    constructor(
        public id?: number,
        public fileContentType?: string,
        public file?: any,
        public employeeEmployeeId?: string,
        public employeeId?: number
    ) {}
}
