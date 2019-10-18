import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StockInProcess } from 'app/shared/model/stock-in-process.model';
import { IStockInProcess } from 'app/shared/model/stock-in-process.model';
import { StockInProcessServiceExtended } from 'app/entities/stock-in-process-extended/stock-in-process.service.extended';
import { StockInProcessDeletePopupComponentExtended } from 'app/entities/stock-in-process-extended/stock-in-process-delete-dialog.component.extended';
import { StockInProcessUpdateComponentExtended } from 'app/entities/stock-in-process-extended/stock-in-process-update.component.extended';
import { StockInProcessDetailComponentExtended } from 'app/entities/stock-in-process-extended/stock-in-process-detail.component.extended';
import { StockInProcessComponentExtended } from 'app/entities/stock-in-process-extended/stock-in-process.component.extended';

@Injectable({ providedIn: 'root' })
export class StockInProcessResolveExtended implements Resolve<IStockInProcess> {
    constructor(private service: StockInProcessServiceExtended) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStockInProcess> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StockInProcess>) => response.ok),
                map((stockInProcess: HttpResponse<StockInProcess>) => stockInProcess.body)
            );
        }
        return of(new StockInProcess());
    }
}

export const stockInProcessRouteExtended: Routes = [
    {
        path: '',
        component: StockInProcessComponentExtended,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockInProcessDetailComponentExtended,
        resolve: {
            stockInProcess: StockInProcessResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockInProcessUpdateComponentExtended,
        resolve: {
            stockInProcess: StockInProcessResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockInProcessUpdateComponentExtended,
        resolve: {
            stockInProcess: StockInProcessResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockInProcessPopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: StockInProcessDeletePopupComponentExtended,
        resolve: {
            stockInProcess: StockInProcessResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
