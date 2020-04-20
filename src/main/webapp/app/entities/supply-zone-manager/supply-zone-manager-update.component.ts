import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { SupplyZoneManagerService } from './supply-zone-manager.service';
import { ISupplyZone } from 'app/shared/model/supply-zone.model';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-supply-zone-manager-update',
    templateUrl: './supply-zone-manager-update.component.html'
})
export class SupplyZoneManagerUpdateComponent implements OnInit {
    supplyZoneManager: ISupplyZoneManager;
    isSaving: boolean;

    supplyzones: ISupplyZone[];

    employees: IEmployee[];
    endDateDp: any;
    createdOn: string;
    updatedOn: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyZoneManagerService: SupplyZoneManagerService,
        protected supplyZoneService: SupplyZoneService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplyZoneManager }) => {
            this.supplyZoneManager = supplyZoneManager;
            this.createdOn = this.supplyZoneManager.createdOn != null ? this.supplyZoneManager.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.supplyZoneManager.updatedOn != null ? this.supplyZoneManager.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.supplyZoneService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyZone[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyZone[]>) => response.body)
            )
            .subscribe((res: ISupplyZone[]) => (this.supplyzones = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.supplyZoneManager.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.supplyZoneManager.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.supplyZoneManager.id !== undefined) {
            this.subscribeToSaveResponse(this.supplyZoneManagerService.update(this.supplyZoneManager));
        } else {
            this.subscribeToSaveResponse(this.supplyZoneManagerService.create(this.supplyZoneManager));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplyZoneManager>>) {
        result.subscribe((res: HttpResponse<ISupplyZoneManager>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackSupplyZoneById(index: number, item: ISupplyZone) {
        return item.id;
    }

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }
}
