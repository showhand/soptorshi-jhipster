/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { TermsAndConditionsDetailComponent } from 'app/entities/terms-and-conditions/terms-and-conditions-detail.component';
import { TermsAndConditions } from 'app/shared/model/terms-and-conditions.model';

describe('Component Tests', () => {
    describe('TermsAndConditions Management Detail Component', () => {
        let comp: TermsAndConditionsDetailComponent;
        let fixture: ComponentFixture<TermsAndConditionsDetailComponent>;
        const route = ({ data: of({ termsAndConditions: new TermsAndConditions(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [TermsAndConditionsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TermsAndConditionsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TermsAndConditionsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.termsAndConditions).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
