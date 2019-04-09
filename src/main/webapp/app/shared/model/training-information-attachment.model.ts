export interface ITrainingInformationAttachment {
    id?: number;
    fileContentType?: string;
    file?: any;
    employeeEmployeeId?: string;
    employeeId?: number;
}

export class TrainingInformationAttachment implements ITrainingInformationAttachment {
    constructor(
        public id?: number,
        public fileContentType?: string,
        public file?: any,
        public employeeEmployeeId?: string,
        public employeeId?: number
    ) {}
}
