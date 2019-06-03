/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { DesignationWiseAllowanceDetailComponent } from 'app/entities/designation-wise-allowance/designation-wise-allowance-detail.component';
import { DesignationWiseAllowance } from 'app/shared/model/designation-wise-allowance.model';

describe('Component Tests', () => {
    describe('DesignationWiseAllowance Management Detail Component', () => {
        let comp: DesignationWiseAllowanceDetailComponent;
        let fixture: ComponentFixture<DesignationWiseAllowanceDetailComponent>;
        const route = ({ data: of({ designationWiseAllowance: new DesignationWiseAllowance(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DesignationWiseAllowanceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DesignationWiseAllowanceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DesignationWiseAllowanceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.designationWiseAllowance).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
