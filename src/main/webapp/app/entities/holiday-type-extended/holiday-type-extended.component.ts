import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { HolidayTypeExtendedService } from './holiday-type-extended.service';
import { HolidayTypeComponent } from 'app/entities/holiday-type';

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
}
