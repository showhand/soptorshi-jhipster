/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPackagingDetailComponent } from 'app/entities/commercial-packaging/commercial-packaging-detail.component';
import { CommercialPackaging } from 'app/shared/model/commercial-packaging.model';

describe('Component Tests', () => {
    describe('CommercialPackaging Management Detail Component', () => {
        let comp: CommercialPackagingDetailComponent;
        let fixture: ComponentFixture<CommercialPackagingDetailComponent>;
        const route = ({ data: of({ commercialPackaging: new CommercialPackaging(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPackagingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommercialPackagingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialPackagingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commercialPackaging).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
