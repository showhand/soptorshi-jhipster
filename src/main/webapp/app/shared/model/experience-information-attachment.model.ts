export interface IExperienceInformationAttachment {
    id?: number;
    fileContentType?: string;
    file?: any;
    employeeEmployeeId?: string;
    employeeId?: number;
}

export class ExperienceInformationAttachment implements IExperienceInformationAttachment {
    constructor(
        public id?: number,
        public fileContentType?: string,
        public file?: any,
        public employeeEmployeeId?: string,
        public employeeId?: number
    ) {}
}
