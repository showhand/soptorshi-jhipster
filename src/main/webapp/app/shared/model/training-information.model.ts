export interface ITrainingInformation {
    id?: number;
    name?: string;
    subject?: string;
    organization?: string;
    employeeId?: number;
}

export class TrainingInformation implements ITrainingInformation {
    constructor(
        public id?: number,
        public name?: string,
        public subject?: string,
        public organization?: string,
        public employeeId?: number
    ) {}
}
