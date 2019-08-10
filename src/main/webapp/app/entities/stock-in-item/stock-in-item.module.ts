import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockInItemComponent,
    StockInItemDetailComponent,
    StockInItemUpdateComponent,
    StockInItemDeletePopupComponent,
    StockInItemDeleteDialogComponent,
    stockInItemRoute,
    stockInItemPopupRoute
} from './';

const ENTITY_STATES = [...stockInItemRoute, ...stockInItemPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockInItemComponent,
        StockInItemDetailComponent,
        StockInItemUpdateComponent,
        StockInItemDeleteDialogComponent,
        StockInItemDeletePopupComponent
    ],
    entryComponents: [StockInItemComponent, StockInItemUpdateComponent, StockInItemDeleteDialogComponent, StockInItemDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockInItemModule {}
