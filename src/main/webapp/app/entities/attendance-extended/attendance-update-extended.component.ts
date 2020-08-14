import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { AttendanceUpdateComponent } from 'app/entities/attendance';
import { AttendanceExtendedService } from 'app/entities/attendance-extended/attendance-extended.service';
import { EmployeeExtendedService } from 'app/entities/employee-extended';
import { AttendanceExcelUploadExtendedService } from 'app/entities/attendance-excel-upload-extended';

@Component({
    selector: 'jhi-attendance-update-extended',
    templateUrl: './attendance-update-extended.component.html'
})
export class AttendanceUpdateExtendedComponent extends AttendanceUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected attendanceService: AttendanceExtendedService,
        protected employeeService: EmployeeExtendedService,
        protected attendanceExcelUploadService: AttendanceExcelUploadExtendedService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, attendanceService, employeeService, attendanceExcelUploadService, activatedRoute);
    }
}
