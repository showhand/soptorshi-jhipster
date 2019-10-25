import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ConversionFactor } from 'app/shared/model/conversion-factor.model';
import { ConversionFactorExtendedService } from './conversion-factor-extended.service';
import { ConversionFactorExtendedComponent } from './conversion-factor-extended.component';
import { ConversionFactorExtendedDetailComponent } from './conversion-factor-extended-detail.component';
import { ConversionFactorExtendedUpdateComponent } from './conversion-factor-extended-update.component';
import { IConversionFactor } from 'app/shared/model/conversion-factor.model';
import { ConversionFactorDeletePopupComponent } from 'app/entities/conversion-factor';

@Injectable({ providedIn: 'root' })
export class ConversionFactorExtendedResolve implements Resolve<IConversionFactor> {
    constructor(private service: ConversionFactorExtendedService) {}

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

export const conversionFactorExtendedRoute: Routes = [
    {
        path: '',
        component: ConversionFactorExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConversionFactors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ConversionFactorExtendedDetailComponent,
        resolve: {
            conversionFactor: ConversionFactorExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConversionFactors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ConversionFactorExtendedUpdateComponent,
        resolve: {
            conversionFactor: ConversionFactorExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConversionFactors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ConversionFactorExtendedUpdateComponent,
        resolve: {
            conversionFactor: ConversionFactorExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConversionFactors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const conversionFactorExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ConversionFactorDeletePopupComponent,
        resolve: {
            conversionFactor: ConversionFactorExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ConversionFactors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
