import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ConversionFactor } from 'app/shared/model/conversion-factor.model';
import { ConversionFactorService } from './conversion-factor.service';
import { ConversionFactorComponent } from './conversion-factor.component';
import { ConversionFactorDetailComponent } from './conversion-factor-detail.component';
import { ConversionFactorUpdateComponent } from './conversion-factor-update.component';
import { ConversionFactorDeletePopupComponent } from './conversion-factor-delete-dialog.component';
import { IConversionFactor } from 'app/shared/model/conversion-factor.model';

@Injectable({ providedIn: 'root' })
export class ConversionFactorResolve implements Resolve<IConversionFactor> {
    constructor(private service: ConversionFactorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IConversionFactor> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ConversionFactor>) => response.ok),
                map((conversionFactor: HttpResponse<ConversionFactor>) => conversionFactor.body)
            );
        }
        return of(new ConversionFactor());
    }
}

export const conversionFactorRoute: Routes = [
    {
        path: '',
        component: ConversionFactorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConversionFactors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ConversionFactorDetailComponent,
        resolve: {
            conversionFactor: ConversionFactorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConversionFactors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ConversionFactorUpdateComponent,
        resolve: {
            conversionFactor: ConversionFactorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConversionFactors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ConversionFactorUpdateComponent,
        resolve: {
            conversionFactor: ConversionFactorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConversionFactors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const conversionFactorPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ConversionFactorDeletePopupComponent,
        resolve: {
            conversionFactor: ConversionFactorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConversionFactors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
