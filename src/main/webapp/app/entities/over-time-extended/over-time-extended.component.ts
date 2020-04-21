import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { OverTimeExtendedService } from './over-time-extended.service';
import { OverTimeComponent } from 'app/entities/over-time';

@Component({
    selector: 'jhi-over-time-extended',
    templateUrl: './over-time-extended.component.html'
})
export class OverTimeExtendedComponent extends OverTimeComponent {
    constructor(
        protected overTimeService: OverTimeExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(overTimeService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
