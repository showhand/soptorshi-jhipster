/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPoStatusDetailComponent } from 'app/entities/commercial-po-status/commercial-po-status-detail.component';
import { CommercialPoStatus } from 'app/shared/model/commercial-po-status.model';

describe('Component Tests', () => {
    describe('CommercialPoStatus Management Detail Component', () => {
        let comp: CommercialPoStatusDetailComponent;
        let fixture: ComponentFixture<CommercialPoStatusDetailComponent>;
        const route = ({ data: of({ commercialPoStatus: new CommercialPoStatus(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPoStatusDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommercialPoStatusDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialPoStatusDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commercialPoStatus).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
