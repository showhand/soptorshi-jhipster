import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockInProcessDeleteDialogExtendedComponent,
    StockInProcessDeletePopupExtendedComponent,
    StockInProcessDetailExtendedComponent,
    StockInProcessExtendedComponent,
    stockInProcessExtendedRoute,
    stockInProcessPopupExtendedRoute,
    StockInProcessUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...stockInProcessExtendedRoute, ...stockInProcessPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockInProcessExtendedComponent,
        StockInProcessDetailExtendedComponent,
        StockInProcessUpdateExtendedComponent,
        StockInProcessDeleteDialogExtendedComponent,
        StockInProcessDeletePopupExtendedComponent
    ],
    entryComponents: [
        StockInProcessExtendedComponent,
        StockInProcessUpdateExtendedComponent,
        StockInProcessDeleteDialogExtendedComponent,
        StockInProcessDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockInProcessExtendedModule {}
