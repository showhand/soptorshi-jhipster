import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { AttendanceExtendedService } from './attendance-extended.service';
import { AttendanceExcelUploadService } from 'app/entities/attendance-excel-upload';
import { AttendanceUpdateComponent } from 'app/entities/attendance';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-attendance-update-extended',
    templateUrl: './attendance-update-extended.component.html'
})
export class AttendanceUpdateExtendedComponent extends AttendanceUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected attendanceService: AttendanceExtendedService,
        protected attendanceExcelUploadService: AttendanceExcelUploadService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, attendanceService, attendanceExcelUploadService, employeeService, activatedRoute);
    }
}
