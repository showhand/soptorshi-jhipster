import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { HolidayTypeExtendedService } from './holiday-type-extended.service';
import { HolidayTypeComponent } from 'app/entities/holiday-type';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IHolidayType } from 'app/shared/model/holiday-type.model';

@Component({
    selector: 'jhi-holiday-type-extended',
    templateUrl: './holiday-type-extended.component.html'
})
export class HolidayTypeExtendedComponent extends HolidayTypeComponent {
    constructor(
        protected holidayTypeService: HolidayTypeExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(holidayTypeService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        if (this.currentSearch) {
            this.holidayTypeService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IHolidayType[]>) => this.paginateHolidayTypes(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.holidayTypeService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IHolidayType[]>) => this.paginateHolidayTypes(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    generateReport() {
        this.holidayTypeService.generateReport();
    }
}
