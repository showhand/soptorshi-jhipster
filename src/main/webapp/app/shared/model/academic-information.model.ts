export interface IAcademicInformation {
    id?: number;
    degree?: string;
    boardOrUniversity?: string;
    passingYear?: number;
    group?: string;
    employeeId?: number;
}

export class AcademicInformation implements IAcademicInformation {
    constructor(
        public id?: number,
        public degree?: string,
        public boardOrUniversity?: string,
        public passingYear?: number,
        public group?: string,
        public employeeId?: number
    ) {}
}
