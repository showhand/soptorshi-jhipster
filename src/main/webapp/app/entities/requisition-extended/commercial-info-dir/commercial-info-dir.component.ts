import { Component, Input, OnInit } from '@angular/core';
import { CommercialBudgetService } from 'app/entities/commercial-budget';
import { CommercialProductInfoService } from 'app/entities/commercial-product-info';
import { ICommercialBudget } from 'app/shared/model/commercial-budget.model';
import { ICommercialProductInfo } from 'app/shared/model/commercial-product-info.model';
import { switchMap } from 'rxjs/operators';

@Component({
    selector: 'jhi-commercial-info-dir',
    templateUrl: './commercial-info-dir.component.html',
    styles: []
})
export class CommercialInfoDirComponent implements OnInit {
    @Input()
    commercialId: number;
    commercialBudget: ICommercialBudget;
    commercialProductInfos: ICommercialProductInfo[];

    constructor(
        private commercialBudgetService: CommercialBudgetService,
        private commercialProductInfoService: CommercialProductInfoService
    ) {}

    ngOnInit() {
        this.commercialBudgetService
            .find(this.commercialId)
            .pipe(
                switchMap(res => {
                    this.commercialBudget = res.body;
                    return this.commercialProductInfoService.query({
                        'commercialBudgetId.equals': res.body.id
                    });
                })
            )
            .subscribe(res => {
                this.commercialProductInfos = res.body;
            });
    }
}
